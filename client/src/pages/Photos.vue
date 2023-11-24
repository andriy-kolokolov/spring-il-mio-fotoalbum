<script>
import {
  SettingOutlined,
  EditOutlined,
  EllipsisOutlined,
} from "@ant-design/icons-vue";
import PhotoService from "../services/PhotoService.js";

export default {
  name: 'Photos',
  components: {
    SettingOutlined,
    EditOutlined,
    EllipsisOutlined,
  },
  data() {
    return {
      photos: [],
    }
  },
  methods: {
    async fetchPhotos() {
      this.photos = await PhotoService.getPhotos();
    },
  },
  mounted() {
    this.fetchPhotos()
  },
}
</script>

<template>
  <a-divider orientation="center">
    <a-typography-title
        :level="3"
    >User List Photos
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
          hoverable
          :style="{height: '100%'}"
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


      </a-card>
    </a-col>

  </a-row>
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