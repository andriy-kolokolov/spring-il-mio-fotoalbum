<script>
import {
  DeleteOutlined,
  EditOutlined,
  EllipsisOutlined,
  MessageOutlined,
  SettingOutlined,
  UserOutlined,
} from "@ant-design/icons-vue";
import PhotoService from "../services/PhotoService.js";
import MessageService from "../services/MessageService.js";
import { message as antMessage } from "ant-design-vue";
import { authState } from "../store/index.js";


export default {
  name: 'Photos',
  computed: {
    authState() {
      return authState
    }
  },
  components: {
    DeleteOutlined,
    SettingOutlined,
    EditOutlined,
    EllipsisOutlined,
    MessageOutlined,
    UserOutlined
  },
  data() {
    return {
      photos: [],
      messageContent: '',
      errors: [],
      showSendMessage: false,
      isSubmitting: false,
      receiverName: '',
      messagePhoto: null,
      fetching: true,
      searchTerm: '',
    }
  },
  methods: {
    async fetchPhotos() {
      this.photos = await PhotoService.getPhotos();
    },
    async sendMessage() {
      this.errors = [];
      const photo = this.messagePhoto;
      const message = this.messageContent;
      const receiverId = photo.user.id

      this.isSubmitting = true
      // to simulate request
      await new Promise(resolve => setTimeout(resolve, 500));

      const messageResponse = await MessageService.sendMessage(receiverId, message)
      if (messageResponse && messageResponse.response &&
          messageResponse.response.data &&
          messageResponse.response.data.errors
      ) {
        this.errors = messageResponse.response.data.errors;
      } else {
        this.messageContent = '';
        this.showSendMessage = false;
        antMessage.success(`Message to user ${ this.messagePhoto.user.username } sent successfully !`)
      }
      this.isSubmitting = false;
    },
    getFieldStatus(fieldName) {
      if (this.errors && this.errors[fieldName]) {
        return 'error';
      }
      return 'success';
    },
    showSendMessageModal(photo) {
      this.messagePhoto = photo;
      this.showSendMessage = true
      this.setMessageModalTitle(photo)
    },
    setMessageModalTitle(photo) {
      this.receiverName = photo.user.username;
    },
    async onSearch() {
      if (this.searchTerm !== '' && this.searchTerm.length > 0) {
        this.fetching = true;
        this.photos = await PhotoService.getPhotosByTitle(this.searchTerm);
        this.fetching = false;
      } else {
        antMessage.warning("Search term is empty.")
      }
    },
    resetSearch() {
      if(this.searchTerm.length > 0) {
        this.searchTerm = '';
        this.fetchPhotos();
      } else {
        antMessage.warning("Search already reset.")
      }
    }
  },
  mounted() {
    this.fetchPhotos()
    this.fetching = false;
  },
}
</script>

<template>
  <a-divider orientation="center">
    <a-typography-title
        :level="3"
        :style="{margin: 0}"
    >User List Photos
    </a-typography-title>
  </a-divider>

  <a-spin
      :spinning="fetching"
      tip="Loading..."
  >

    <a-row
        :style="{margin: '0', padding: '20px', width: '100%'}"
        v-if="!fetching"
    >
      <a-col
          :span="24"
          align="center"
      >
        <a-space :size="12">
          <a-button
              @click="resetSearch"
              type="primary"
              shape="round"
          >
            Reset
          </a-button>

          <a-input-search
              v-model:value="searchTerm"
              placeholder="Search by title"
              style="min-width: 250px;"
              @search="onSearch"
          />

        </a-space>
      </a-col>
    </a-row>

    <a-row
        :style="{margin: '0', padding: '20px', width: '100%'}"
        :gutter="[36, 36]"
        v-if="!fetching && photos.length > 0"
    >
      <a-col
          :xs="24"
          :md="12"
          :lg="8"
          :xl="6"
          v-for="photo in photos"
      >
        <a-card
            class="ms-card ms-photo-transition"
            hoverable
            :style="{height: '100%', display: 'flex', flexDirection: 'column'}"
            :body-style="{flexGrow: '1'}"
        >
          <template #cover>
            <img
                alt="example"
                src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
            />
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

          <template v-if="authState.isAuthenticated" #actions>
            <a-button
                type="link"
                @click="showSendMessageModal(photo)"
            >
              <message-outlined :style="{fontSize: '16px'}" key="message"/>
            </a-button>
          </template>
        </a-card>
      </a-col>
    </a-row>

    <a-typography-title
        :level="3"
        :style="{paddingInline: '20px'}"
        v-else
    >
      No photos found for request: {{ searchTerm }}
    </a-typography-title>
  </a-spin>

  <!--    SEND MESSAGE MODAL    -->
  <a-modal
      ref="modalRef"
      v-model:open="showSendMessage"
  >
    <template #title>
      Send message to
      <a-tag :style="{fontSize: '16px'}" color="blue">
        <user-outlined/>
        {{ receiverName }}
      </a-tag>
    </template>
    <a-form
        @submit.prevent="sendMessage()"
        layout="vertical"
    >
      <a-form-item
          label="Message"
          :help="errors && errors.content"
          :validate-status="getFieldStatus('content')"
      >
        <a-input
            v-model:value="messageContent"
        >

        </a-input>
      </a-form-item>

      <a-button
          html-type="submit"
          type="primary"
          :loading="isSubmitting"
      >
        Send
      </a-button>
    </a-form>
    <template #footer>
      <!--    to hide default footer buttons    -->
    </template>
  </a-modal>


</template>

<style lang="scss">
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.ms-photo-transition {
  animation: fadeInUp .6s ease-out;
}
</style>