<template>
  <div>
    <Notification />
    <router-view />
  </div>
</template>

<script setup>
import Notification from './components/Notification.vue'
</script>

<style lang="scss">
$main-bg-color: #17171a;
#app {
  font-family: 'SF Pro Text';
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

body {
  background-color: $main-bg-color;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

::-webkit-scrollbar {
  width: 8px;
}

::-webkit-scrollbar-track {
  background-color: transparent;
}

::-webkit-scrollbar-thumb {
  background-color: #666;
  border-radius: 6px;
}

::-webkit-scrollbar-thumb:hover {
  background-color: #444;
}
</style>
