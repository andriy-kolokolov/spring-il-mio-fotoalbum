<script>
import locale from 'ant-design-vue/es/date-picker/locale/it_IT';
import dayjs from "dayjs";

dayjs.locale('it-IT');

export default {
  computed: {
    locale,
  }
}
</script>

<template>
  <a-config-provider :locale="locale">
    <a-layout :style="{height: '100vh'}">

      <a-layout-content>

        <slot>
          <!--    content    -->
        </slot>

      </a-layout-content>

    </a-layout>

  </a-config-provider>
</template>

<style lang="scss">

</style>