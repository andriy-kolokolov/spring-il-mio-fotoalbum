<script>
import {
  MessageOutlined,
  UserOutlined,
} from "@ant-design/icons-vue";
import MessageService from "../services/MessageService.js";
import dayjs from "dayjs";

export default {
  name: 'Messages',
  components: {
    MessageOutlined,
    UserOutlined,
  },
  data() {
    return {
      sentMessages: [],
      receivedMessages: [],
      fetching: true,
    }
  },
  methods: {
    dayjs,
    async loadMessages() {
      this.sentMessages = await MessageService.getUserSentMessages();
      this.receivedMessages = await MessageService.getUserReceivedMessages();
      this.fetching = false;
    },
    formatDate(date) {
      return dayjs(date).format('MMMM D, YYYY HH:mm')
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

  <a-spin
    :spinning="fetching"
    tip="Loading..."
  >
    <a-row
        v-if="!fetching"
        :gutter="[12, 12]"
        :style="{margin: 0, paddingInline: '15px'}"
    >
      <!--    SENT    -->
      <a-col
          :xs="24"
          :md="12"
          class="ms-fade-in-transition"
          v-if="sentMessages.length > 0"
      >
        <h3>Sent Messages</h3>
        <a-comment
            v-for="message in sentMessages"
            :key="message.id"
            :datetime="formatDate(message.sentAt)"
        >
          <template #author>
            <a>You to: {{ message.receiverUsername }}</a>
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
      </a-col>

      <a-col
          :xs="24"
          :md="12"
          v-else
          class="ms-fade-in-transition"
      >
        <a-typography-title
            :level="5"
        >
          You have no sent messages yet ...
        </a-typography-title>
      </a-col>

      <!--    RECEIVED    -->
      <a-col
          :xs="24"
          :md="12"
          class="ms-fade-in-transition"
          v-if="receivedMessages.length > 0"
      >
        <h3>Received Messages</h3>
        <a-comment

            v-for="message in receivedMessages"
            :key="message.id"
            :datetime="formatDate(message.sentAt)"
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
      </a-col>

      <a-col
          :xs="24"
          :md="12"
          v-else
          class="ms-fade-in-transition"
      >
        <a-typography-title
            :level="5"
            :style="{paddingInline: '20px'}"
        >
          You have no received messages yet ...
        </a-typography-title>
      </a-col>

    </a-row>
  </a-spin>
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