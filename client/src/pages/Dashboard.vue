<script>
import {
  EditOutlined,
  DeleteOutlined,
  SettingOutlined,
  UploadOutlined,
  PlusOutlined,
} from "@ant-design/icons-vue";
import PhotoService from "../services/PhotoService.js";
import { authState } from "../store/index.js";
import { message } from "ant-design-vue";
import CategoryService from "../services/CategoryService.js";
import dayjs from "dayjs";

export default {
  name: 'Dashboard',
  components: {
    SettingOutlined,
    EditOutlined,
    DeleteOutlined,
    UploadOutlined,
    PlusOutlined,
  },
  data() {
    return {
      photos: [],
      photoCategories: [],
      allCategories: [],
      showCreate: false,
      showUpdate: false,
      editingPhoto: null,
      selectedCategories: null,
      form: {
        userId: authState.user.id,
        title: '',
        description: '',
        isVisible: false,
        categoryIds: [],
        url: '',
      },
      isSubmitting: false,
      errors: [],
      success: false,
      deletingPhotoId: null,
      fetching: true,
      fileList: [],
      uploadProgress: 0,
      previewVisible: false,
    }
  },
  methods: {
    dayjs,
    async fetchUserPhotos() {
      this.photos = await PhotoService.getPhotosByUserId(authState.user.id);
    },
    async fetchAllCategories() {
      this.allCategories = await CategoryService.getAll();
    },
    async fetchPhotoCategories(photoId) {
      const response = await CategoryService.getByPhotoId(photoId)
      if (response.status === 404) {
        this.photoCategories = [];
      }
      this.photoCategories = response;

    },
    resetForm() {
      this.form = {
        userId: authState.user.id,
        title: '',
        description: '',
        isVisible: false,
        categoryIds: [],
        url: '',
      };
      this.fileList = [];
    },

    openCreate() {
      this.showCreate = true;
      this.selectedCategories = [];
    },
    async handleCreate() {
      // reset errors and alerts
      this.errors = [];
      this.success = false;
      this.isSubmitting = true;
      await new Promise(resolve => setTimeout(resolve, 350));

      try {
        this.form.categoryIds = this.selectedCategories;
        // set photo url
        this.form.url = `http://localhost:8080/uploads/${ this.fileList[0].name }`
        const response = await PhotoService.createPhoto(this.form);
        console.log(this.form)
        if (response.success) {
          this.success = true;
          await this.fetchUserPhotos();
          this.showCreate = false;
          this.resetForm();
          await new Promise(resolve => setTimeout(resolve, 350));
          message.success('New photo created successfully!')
        } else {
          this.errors = response.response.data.errors;
          console.log(this.errors)
        }
      } catch (error) {
        this.errors = error.response.data;
      } finally {
        this.isSubmitting = false;
      }
    },

    openUpdate(photo) {
      this.fetchPhotoCategories(photo.id).then(() => {
        if (this.photoCategories.length > 0) {
          this.selectedCategories = this.photoCategories.map(c => c.id);
        } else {
          this.selectedCategories = [];
        }
      });
      this.editingPhoto = { ...photo };
      this.showUpdate = true;
    },

    async handleUpdate() {
      this.isSubmitting = true;
      try {
        this.editingPhoto.categoryIds = this.selectedCategories;
        const response = await PhotoService.updatePhoto(this.editingPhoto.id, this.editingPhoto);
        if (response.success) {
          message.success('Photo updated successfully!');
          await this.fetchUserPhotos();
          this.showUpdate = false;
        } else {
          this.errors = response.response.data.errors;
        }
      } catch (error) {
        this.errors = error.response.data;
      } finally {
        this.isSubmitting = false;
      }
    },
    getFieldStatus(fieldName) {
      if (this.errors && this.errors[fieldName]) {
        return 'error';
      }
      return 'success';
    },
    async handleDelete(photoId) {
      try {
        this.deletingPhotoId = photoId;
        await new Promise(resolve => setTimeout(resolve, 500));

        const response = await PhotoService.deletePhoto(photoId);
        if (response.success === true) {
          message.success(response.message);
          await this.fetchUserPhotos();
        }
      } catch (error) {
        console.error(error.data.response);
        this.deletingPhotoId = null;
      }
    },
    async handleUpload(request) {

      request.onProgress = (e) => {
        const percent = Math.floor((e.loaded / e.total) * 100);
        this.uploadProgress = percent;
        this.fileList[0].percent = percent;
      };

      try {
        const response = await PhotoService.uploadPhoto(request);
        // Reset progress or handle success state
        this.uploadProgress = 100; // or set to 0 if you prefer
        this.fileList[0].status = 'done'

      } catch (error) {
        console.error(error);
        // Handle error state
        this.uploadProgress = 0;
      }
    },
    getPhotoUrl(photo) {
      return photo.url === null ?
          'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png' :
          photo.url
    }
  },
  mounted() {
    this.fetchUserPhotos()
    this.fetchAllCategories();
    this.fetching = false;
  }
}

</script>

<template>
  <a-divider orientation="center">
    <a-typography-title
        :level="3"
    >Your Photos
    </a-typography-title>
  </a-divider>
  <a-spin
      :spinning="fetching"
      tip="Loading..."
  >
    <a-row
        :style="{paddingInline: '20px', width: '100%'}"
        align="center"
        v-if="!fetching"

    >
      <a-col>
        <a-button
            type="primary"
            @click="openCreate"
        >
          Add new one +
        </a-button>
      </a-col>
    </a-row>

    <a-row
        :style="{margin: '0', padding: '20px', width: '100%'}"
        :gutter="[36, 36]"
        v-if="photos.length > 0 && !fetching"
    >
      <a-col
          :xs="24"
          :md="12"
          :lg="8"
          :xl="6"
          v-for="photo in photos"
          :class="{ 'fade-out': deletingPhotoId === photo.id }"
          :key="photo.id"
      >
        <a-card
            class="ms-card ms-photo-transition"
            :style="{height: '100%', display: 'flex', flexDirection: 'column'}"
            :body-style="{flexGrow: '1'}"
        >
          <template #cover>
            <img
                alt="example"
                :src="getPhotoUrl(photo)"

            />
          </template>

          <template #actions>
            <a-button
                type="link"
                @click="openUpdate(photo)"
            >
              <edit-outlined :style="{fontSize: '16px'}" key="edit"/>
            </a-button>
            <a-popconfirm
                title="Are you sure delete this photo?"
                ok-text="Yes"
                cancel-text="No"
                @confirm="handleDelete(photo.id)"
            >
              <a-button
                  type="link"
              >
                <delete-outlined :style="{color: 'red', fontSize: '16px'}" key="ellipsis"/>
              </a-button>
            </a-popconfirm>
          </template>

          <a-card-meta
              :title="photo.title"
              :description="photo.description"
          >
          </a-card-meta>
          <a-divider
              :style="{fontSize: '14px'}"
              orientation="right"
              orientation-margin="0px"
          >Made by
          </a-divider>
          <a-card-meta
              :style="{textAlign: 'right'}"
              :description="photo.user.username"
          >
          </a-card-meta>
          <a-divider
              :style="{fontSize: '14px'}"
              orientation="right"
              orientation-margin="0px"
          >Categories
          </a-divider>
          <a-card-meta
              v-for="category in photo.categories"
              :style="{textAlign: 'right'}"
              :description="category.name"
          >
          </a-card-meta>
        </a-card>
      </a-col>
    </a-row>

    <a-typography-title
        :level="3"
        class="ms-photo-transition"
        :style="{paddingInline: '20px'}"
        v-else
    >
      You have no photos :(
    </a-typography-title>
  </a-spin>
  <!--    CREATE    -->
  <a-modal
      ref="modalRef"
      v-model:open="showCreate"
      title="Add new photo"
  >
    <a-form
        @submit.prevent="handleCreate"
        layout="vertical"
    >

      <a-form-item
          label="Title"
          :help="errors && errors.title"
          :validate-status="getFieldStatus('title')"
      >
        <a-input
            v-model:value="form.title"
            :status="getFieldStatus('title')"
        >

        </a-input>
      </a-form-item>
      <a-form-item
          label="Description"
          :help="errors && errors.description"
          :validate-status="getFieldStatus('description')"
      >
        <a-input
            v-model:value="form.description"
            :status="getFieldStatus('description')"
        >

        </a-input>
      </a-form-item>
      <a-form-item
      >
        <a-checkbox
            v-model:checked="form.isVisible"
        >Make Visible
        </a-checkbox>
      </a-form-item>

      <!-- Category Checkboxes -->
      <a-form-item label="Categories">
        <a-checkbox-group v-model:value="selectedCategories">
          <a-row>
            <a-col v-for="category in allCategories" :key="category.id" span="8">
              <a-checkbox :value="category.id">{{ category.name }}</a-checkbox>
            </a-col>
          </a-row>
        </a-checkbox-group>
      </a-form-item>

      <!--    PHOTO UPLOAD    -->
      <a-form-item label="Photo">
        <a-upload
            v-model:file-list="fileList"
            list-type="picture-card"
            :custom-request="handleUpload"
            :showInfo="false"
            @preview="() => previewVisible = true"

        >
          <div v-if="fileList.length < 1">
            <plus-outlined/>
            <div style="margin-top: 8px">Upload</div>
          </div>
        </a-upload>

        <a-modal
            :open="previewVisible"
            :title="fileList.length > 0 ? fileList[0].name : ''"
            :footer="null"
            @cancel="() => previewVisible = false"
        >
          <img
              alt="photo preview"
              style="width: 100%"
              :src="`http://localhost:8080/uploads/${fileList[0].name}`"
          />
        </a-modal>

      </a-form-item>

      <a-button
          html-type="submit"
          type="primary"
          :loading="isSubmitting"
      >
        Create
      </a-button>
    </a-form>
    <template #footer>
      <!--    to hide default footer buttons    -->
    </template>
  </a-modal>


  <!-- Update Modal -->
  <a-modal
      v-model:open="showUpdate"
      title="Update photo"
      :confirm-loading="isSubmitting"
  >
    <a-form
        layout="vertical"
        @submit.prevent="handleUpdate"
    >
      <a-form-item label="Title">
        <a-input v-model:value="editingPhoto.title"/>
      </a-form-item>
      <a-form-item label="Description">
        <a-textarea v-model:value="editingPhoto.description"/>
      </a-form-item>
      <a-form-item>
        <a-checkbox
            v-model:checked="editingPhoto.isVisible"
        >Make Visible
        </a-checkbox>
      </a-form-item>

      <!-- Category Checkboxes -->
      <a-form-item label="Categories">
        <a-checkbox-group v-model:value="selectedCategories">
          <a-row>
            <a-col v-for="category in allCategories" :key="category.id" span="8">
              <a-checkbox :value="category.id">{{ category.name }}</a-checkbox>
            </a-col>
          </a-row>
        </a-checkbox-group>
      </a-form-item>

      <a-button
          html-type="submit"
          type="primary"
          :loading="isSubmitting"
      >
        Update
      </a-button>
    </a-form>

    <template #footer>
      <!--    to hide default footer buttons    -->
    </template>
  </a-modal>
</template>

<style lang="scss">
.fade-out {
  transition: opacity 0.5s ease-out;
  opacity: 0;
}

.ant-upload-list-item-actions {
  button:last-child {
    display: none;
  }
}
</style>