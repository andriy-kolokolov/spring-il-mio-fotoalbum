<script>
import { MsHeader } from "../Components/";

import itIT from 'ant-design-vue/es/locale/it_IT';

export default {
  components: {
    MsHeader,
  },
  data() {
    return {
      itIT,
    }
  },
};
</script>

<template>
  <a-config-provider
      :locale="itIT"
  >

    <a-layout>

      <a-layout
          :style="{
            minHeight: '100vh',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'spaceBetween'
          }"
      >
        <!--    LAYOUT HEADER    -->
        <a-layout-header
            class="ms-header"
            :style="{
              paddingInline: '20px',
              background: '#fff',
            }"
        >

          <!--    HEADER    -->
          <ms-header
          />

        </a-layout-header>

        <!--    LAYOUT CONTENT    -->
        <a-layout-content
            :style="{
              height: '100%',
              flex: 1,
              display: 'flex',
              flexDirection: 'column',
            }"
        >
          <slot>
            <!--    PAGE CONTENT    -->
            <router-view/>

          </slot>
        </a-layout-content>

        <!--    LAYOUT FOOTER    -->
        <a-layout-footer
            class="ms-footer"
            :style="{
              background: '#fff',
              fontSize: '12px',
              textAlign: 'right',
              padding: '10px 20px',
          }"
        >
          @Photoalbum by Andriy Kolokolov v 1.0.0 | All Rights Reserved
        </a-layout-footer>

      </a-layout>

    </a-layout>

  </a-config-provider>
</template>

<style lang="scss">

</style>