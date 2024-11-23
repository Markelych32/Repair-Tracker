<template>
  <transition name="fade">
    <div v-if="visible" class="notification">
      <span>{{ message }}</span>
      <button @click="close">✕</button>
    </div>
  </transition>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const props = defineProps({
  message: {
    type: String,
    required: true,
  },
})

const visible = ref(true)

const close = () => {
  visible.value = false
}

let timer

onMounted(() => {
  timer = setTimeout(() => {
    close()
  }, 5000)
})

onUnmounted(() => {
  clearTimeout(timer)
})
</script>

<style lang="scss" scoped>
.notification {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #cb0a0a;
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

/* Анимация появления и исчезновения */
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
