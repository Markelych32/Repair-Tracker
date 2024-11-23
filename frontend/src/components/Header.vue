<template>
  <div class="header">
    <h2 class="name">SpringBoard</h2>
    <div class="buttons">
      <FilledButton @click="router.push('/login')" btnText="Log In" />
      <OutlinedButton @click="logout" btnText="Log Out" />
    </div>
  </div>
</template>

<script setup>
import { useGlobalState } from '@/stores/GlobalStore'
import OutlinedButton from './OutlinedButton.vue'
import FilledButton from './FilledButton.vue'
import router from '@/router'

const globalState = useGlobalState()
const logout = () => {
  globalState.clearUser()
}
</script>

<style lang="scss" scoped>
$main-bg-color: #17171a;
$elem-bg-color: #3e4045;
$accent-color: #eab629;

.header {
  width: 80%;
  margin: 0 auto;
  padding-top: 20px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.name {
  color: $accent-color;
  font-size: 26px;
  font-weight: 300;
}

.buttons {
  display: flex;
  flex-direction: row;
  gap: 8px;
}
</style>
