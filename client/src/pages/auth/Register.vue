<script>
import AuthService from '../../services/AuthService.js';

export default {
  name: 'Register',
  data() {
    return {
      form: {
        username: '',
        email: '',
        password: '',
      },
      errorMessage: null,
      isSubmitting: false,
    }
  },
  methods: {
    async handleSubmit() {
      this.isSubmitting = true;
      try {
        await new Promise(resolve => setTimeout(resolve, 500));

        const response = await AuthService.register(this.form);

        if (response.success) {

        } else {
          console.log(response)
        }
      } catch (error) {
        console.error(error.response.data)
        this.errorMessage = error.response.data.message;
      } finally {
        this.isSubmitting = false;
      }
    }
  }
}
</script>

<template>

  <a-alert
      v-if="errorMessage"
      message="Error"
      :description="errorMessage"
      type="error"
      show-icon
  />

  <a-form
      @submit.prevent="handleSubmit"
      :model="form"
  >
    <a-form-item
        label="Username"
    >
      <a-input v-model:value="form.username" placeholder="Username"></a-input>

    </a-form-item>

    <a-form-item
        label="Email"
    >
      <a-input v-model:value="form.email" type="email" placeholder="Email"></a-input>
    </a-form-item>

    <a-form-item
        label="Password"
    >
      <a-input v-model:value="form.password" type="password" placeholder="Password"></a-input>
    </a-form-item>

    <a-form-item>
      <a-button type="primary" :loading="isSubmitting" html-type="submit">Register</a-button>
    </a-form-item>
  </a-form>
</template>

<style lang="scss">

</style>