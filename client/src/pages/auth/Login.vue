<script>
import AuthService from '../../services/AuthService.js';
import { LoginOutlined } from "@ant-design/icons-vue";

export default {
  name: 'Register',
  components: {
    LoginOutlined
  },
  data() {
    return {
      form: {
        username: '',
        password: '',
      },
      success: false,
      isSubmitting: false,
      errorMessage: null,
    }
  },
  methods: {
    async handleSubmit() {
      this.isSubmitting = true;
      // reset errors and alerts
      this.errorMessage = null;
      this.success = false;
      try {
        await new Promise(resolve => setTimeout(resolve, 500));
        const response = await AuthService.login(this.form);
        if (response.success) {
          this.success = true;
          await new Promise(resolve => setTimeout(resolve, 500));
          this.$router.push('/dashboard')
        }
      } catch (error) {
        this.errorMessage = error.response.data.message;
      } finally {
        this.isSubmitting = false;
      }
    },
    getFieldStatus() {
      return this.errorMessage !== null ?
          'error' : 'success'
    }
  }
}
</script>

<template>

  <a-flex
      justify="center"
      align="center"
      :style="{
        height: '80vh'
      }"
  >
    <a-card
        title="Login form"
        class="ms-card"
        :style="{
          minWidth: '600px',
          padding: '15px 15px 0 15px',
          textAlign: 'center'
        }"
    >
      <a-form
          @submit.prevent="handleSubmit"
          :model="form"
          layout="vertical"
      >
        <a-form-item
            v-if="success"
        >
          <a-alert
              message="Login success!"
              type="success"
              show-icon
          />
        </a-form-item>

        <a-form-item
            label="Username"
            :help="errorMessage"
            :validate-status="getFieldStatus('username')"
        >
          <a-input
              v-model:value="form.username"
              placeholder="Username"
              :status="getFieldStatus('username')"
              :min="4"
          ></a-input>

        </a-form-item>

        <a-form-item
            label="Password"
            :help="errorMessage"
            :validate-status="getFieldStatus('password')"
        >
          <a-input
              v-model:value="form.password"
              type="password"
              placeholder="Password"
              :status="getFieldStatus('password')"
          ></a-input>
        </a-form-item>

        <a-form-item>

          <a-typography-paragraph>
            Use <a-tag color="orange">Alice</a-tag> for username and <a-tag color="orange">123123</a-tag> for password.
          </a-typography-paragraph>
        </a-form-item>

        <a-form-item>
          <a-button
              :style="{display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100%'}"
              type="primary"
              :loading="isSubmitting"
              html-type="submit"
          >Login
            <LoginOutlined/>
          </a-button>

        </a-form-item>
      </a-form>
    </a-card>
  </a-flex>

</template>

<style lang="scss">

</style>