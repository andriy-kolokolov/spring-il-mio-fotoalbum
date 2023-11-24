<script>
import {
  HomeOutlined,
  LoginOutlined,
  LogoutOutlined,
  PictureOutlined
} from "@ant-design/icons-vue";
import { h } from "vue";
import { authState } from "../store/index.js";
import AuthService from "../services/AuthService.js";

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
    handleLogoutClick() {
      AuthService.logout();
    }
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
    authenticated() {
      return authState.isAuthenticated;
    },
    getUserName() {
      return this.$user.username;
    },
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
  <a-row justify="space-between">
    <a-col
        :xs="12"
    >
      <a-menu
          mode="horizontal"
          :selectedKeys="[activeKey]"
          :items="computedMenuItems"
      />
    </a-col>
    <a-col
        align="end"
        :xs="12"
    >
      <a-space
        :size="15"
      >
        <a-typography-title
          :level="5"
          :style="{whiteSpace: 'nowrap', margin: 0}"
        >
          Welcome,  {{ getUserName }}
        </a-typography-title>
        <a-button
            v-if="authenticated"
            type="primary"
            danger
            @click="handleLogoutClick"
        >
          Logout
        </a-button>
      </a-space>
    </a-col>
  </a-row>

</template>

<style lang="scss">

</style>