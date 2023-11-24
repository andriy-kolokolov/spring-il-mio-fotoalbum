<script>
import {
  HomeOutlined,
  LoginOutlined,
  LogoutOutlined,
  PictureOutlined
} from "@ant-design/icons-vue";
import { h } from "vue";
import { authState } from "../store/index.js";

export default {
  name: 'Header',
  components: {
    HomeOutlined,
    LoginOutlined,
    LogoutOutlined,
    PictureOutlined,
  },
  data() {
    return {
      activeKey: this.$route.path,
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
  },
  computed: {
    computedMenuItems() {
      // permitted for all nav
      let menuItems = [
        {
          key: '/photos',
          icon: () => h(PictureOutlined),
          label: 'Photos',
          title: 'Photos',
          onClick: () => this.navigateTo('/photos')
        },
      ];
      // authenticated nav
      if (authState.isAuthenticated) {
        menuItems.push(
            {
              key: '/dashboard',
              icon: () => h(HomeOutlined),
              label: 'Dashboard',
              title: 'Dashboard',
              onClick: () => this.navigateTo('/dashboard')
            }, {
              key: '/logout',
              icon: () => h(LogoutOutlined),
              label: 'Logout',
              title: 'Logout',
              onClick: () => this.navigateTo('/logout'),
              style: { color: 'red', borderColor: 'red' }
            },
        )
      } else {
        menuItems.push(
            {
              key: '/register',
              icon: () => h(LoginOutlined),
              label: 'Register',
              title: 'Register',
              onClick: () => this.navigateTo('/register')
            },
            {
              key: '/login',
              icon: () => h(LoginOutlined),
              label: 'Login',
              title: 'Login',
              onClick: () => this.navigateTo('/login')
            }
        );
      }

      return menuItems;
    }
  }
}
</script>

<template>
  <a-menu
      mode="horizontal"
      :selectedKeys="[activeKey]"
      :items="computedMenuItems"
  />
</template>

<style lang="scss">

</style>