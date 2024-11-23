<template>
  <transition name="fade">
    <div v-if="globalState.notification.value" class="notification">
      <span>{{ globalState.notification.value }}</span>
      <button @click="close">✕</button>
    </div>
  </transition>
</template>

<script setup>
import { onMounted, onUnmounted, watch } from 'vue'
import { useGlobalState } from '@/stores/GlobalStore'

const globalState = useGlobalState()

const close = () => {
  globalState.hideNotification()
}

let timer

const startTimer = () => {
  clearTimeout(timer)
  timer = setTimeout(() => {
    close()
  }, 5000)
}

onMounted(() => {
  if (globalState.notification.value) {
    startTimer()
  }
})

onUnmounted(() => {
  clearTimeout(timer)
})

watch(
  () => globalState.notification.value,
  (newValue) => {
    if (newValue) {
      startTimer()
    }
  }
)
</script>

<style lang="scss" scoped>
.notification {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: darken(#cb0a0a, 10%);
  color: #17171a;
  font-weight: 500;
  padding: 20px;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 300px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.notification button {
  background: none;
  border: none;
  color: #17171a;
  font-size: 20px;
  cursor: pointer;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s, transform 0.3s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(-20px);
}
</style>
