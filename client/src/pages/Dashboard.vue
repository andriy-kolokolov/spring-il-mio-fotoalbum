<script>
import { EditOutlined, DeleteOutlined, SettingOutlined } from "@ant-design/icons-vue";
import PhotoService from "../services/PhotoService.js";

export default {
  name: 'Dashboard',
  components: {
    SettingOutlined,
    EditOutlined,
    DeleteOutlined,
  },
  data() {
    return {
      photos: [],
    }
  },
  methods: {
    async fetchUserPhotos() {
      this.photos = await PhotoService.getPhotosByUserId(this.$user.id);
    },
  },
  mounted() {
    this.fetchUserPhotos();
  },
}

</script>

<template>
  <a-divider orientation="center">
    <a-typography-title
        :level="3"
    >Your Photos
    </a-typography-title>
  </a-divider>
  <a-row
      :style="{margin: '0', padding: '20px', width: '100%'}"
      :gutter="[36, 36]"
  >
    <a-col
        :xs="24"
        :md="12"
        :lg="8"
        :xxl="6"
        v-for="photo in photos"
    >
      <a-card
          class="ms-card ms-photo-transition"
          :style="{height: '100%'}"
      >
        <template #cover>
          <img
              alt="example"
              src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
          />
        </template>

        <template #actions>
          <edit-outlined key="edit" />
          <delete-outlined :style="{color: 'red'}" key="ellipsis" />
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
            description="You"
        >
        </a-card-meta>

      </a-card>
    </a-col>

  </a-row>
</template>

<style lang="scss">

</style>