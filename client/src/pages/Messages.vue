<script>
import {
  MessageOutlined,
  UserOutlined,
} from "@ant-design/icons-vue";
import MessageService from "../services/MessageService.js";

export default {
  name: 'Messages',
  components: {
    MessageOutlined,
    UserOutlined,
  },
  data() {
    return {
      sentMessages: [],
      receivedMessages: []
    }
  },
  methods: {
    async loadMessages() {
      this.sentMessages = await MessageService.getUserSentMessages();
      this.receivedMessages = await MessageService.getUserReceivedMessages();
    }
  },
  mounted() {
    this.loadMessages();
  },
}
</script>

<template>
  <a-divider orientation="center">
    <a-typography-title
        :level="3"
    >Your Messages
    </a-typography-title>
  </a-divider>

  <a-row
      :gutter="[12, 12]"
      :style="{margin: 0, paddingInline: '15px'}"
  >
    <!--    SENT    -->
    <a-col
        :xs="24"
        :md="12"
    >
      <h3>Sent Messages</h3>
      <a-comment
          class="ms-fade-in-transition"
          v-for="message in sentMessages"
          v-if="sentMessages.length > 0"
          :key="message.id"
      >
        <template #author>
          <a>{{ message.senderUsername }}</a>
        </template>
        <template #avatar>
          <a-avatar>
            <user-outlined/>
          </a-avatar>
        </template>
        <template #content>
          <p>{{ message.content }}</p>
        </template>
      </a-comment>

      <a-typography-title
          v-else
          :level="5"
          class="ms-fade-in-transition"
      >
        You have no sent messages yet ...
      </a-typography-title>
    </a-col>

    <!--    RECEIVED    -->
    <a-col
        :xs="24"
        :md="12"
    >
      <h3>Received Messages</h3>
      <a-comment
          class="ms-fade-in-transition"
          v-if="receivedMessages.length > 0"
          v-for="message in receivedMessages"
          :key="message.id"
      >
        <template #author>
          <a>{{ message.senderUsername }}</a>
        </template>
        <template #avatar>
          <a-avatar>
            <user-outlined/>
          </a-avatar>
        </template>
        <template #content>
          <p>{{ message.content }}</p>
        </template>
      </a-comment>

      <a-typography-title
          v-else
          :level="5"
          class="ms-fade-in-transition"
      >
        You have no received messages yet ...
      </a-typography-title>
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

.ms-fade-in-transition {
  animation: fadeInUp .6s ease-out;
}
</style>