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
      errors: null,
      success: false,
      userExists: null,
      isSubmitting: false,
    }
  },
  methods: {
    async handleSubmit() {
      this.isSubmitting = true;
      // reset errors and alerts
      this.errors = null;
      this.success = false;
      this.userExists = null;
      try {
        // to simulate request
        await new Promise(resolve => setTimeout(resolve, 500));
        const response = await AuthService.register(this.form);
        if (response.success) {
          this.success = true;
        }
      } catch (error) {
        if (error.response.data.status === 409) { // username conflict error status
          this.userExists = error.response.data.errors;
        }
        this.errors = error.response.data.errors;
      } finally {
        this.isSubmitting = false;
      }
    },
    getFieldStatus(fieldName) {
      if (this.errors && this.errors[fieldName]) {
        return 'error';
      }
      return 'success';
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
        title="Registration form"
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
              message="Registration success, you can login now!"
              type="success"
              show-icon
          />
        </a-form-item>
        <a-form-item
            v-if="userExists"
        >
          <a-alert
              message="User already exists."
              type="error"
              show-icon
          />
        </a-form-item>

        <a-form-item
            label="Username"
            :help="errors && errors.username"
            :validate-status="getFieldStatus('username')"
        >
          <a-input
              v-model:value="form.username"
              placeholder="Username"
              :status="getFieldStatus('username')"
          ></a-input>

        </a-form-item>

        <a-form-item
            label="Password"
            :help="errors && errors.password"
            :validate-status="getFieldStatus('password')"
        >
          <a-input
              v-model:value="form.password"
              type="password"
              placeholder="Password"
              :status="getFieldStatus('password')"
          ></a-input>
        </a-form-item>

        <a-form-item
        >
          <a-button
              :style="{display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100%'}"
              type="primary"
              :loading="isSubmitting"
              html-type="submit"
          >Register <LoginOutlined/>
          </a-button>

        </a-form-item>
      </a-form>
    </a-card>
  </a-flex>

</template>

<style lang="scss">

</style>