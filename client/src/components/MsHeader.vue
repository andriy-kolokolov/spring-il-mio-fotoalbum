<script>
import { HomeOutlined, PictureOutlined } from "@ant-design/icons-vue";
import { h } from "vue";

export default {
  name: 'Header',
  components: {
    HomeOutlined,
    PictureOutlined,
  },
  data() {
    return {
      activeKey: this.$route.path,
      menuItems: [
        {
          key: '/dashboard',
          icon: () => h(HomeOutlined),
          label: 'Dashboard',
          title: 'Dashboard',
          onClick: () => this.navigateTo('/dashboard')
        },
        {
          key: '/photos',
          icon: () => h(PictureOutlined),
          label: 'Photos',
          title: 'Photos',
          onClick: () => this.navigateTo('/photos')
        },
      ],
    }
  },
  methods: {
    navigateTo(path) {
      if (this.$route.path !== path) {
        this.$router.push(path);
      }
    },
    updateActiveKey() {
      this.activeKey = this.$route.path;
    },
  },
  watch: {
    '$route'() {
      this.updateActiveKey();
    },
  },
  mounted() {
    this.updateActiveKey();
  }
}
</script>

<template>
  <a-menu
      mode="horizontal"
      :selectedKeys="[activeKey]"
      :items="menuItems"
  />
</template>

<style lang="scss">

</style>