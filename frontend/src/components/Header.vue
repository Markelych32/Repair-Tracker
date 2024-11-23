<template>
  <div class="header">
    <img
      @click="router.push('/')"
      class="name"
      src="../assets/newlogo.png"
      alt="SpringBoard"
    />
    <div class="buttons">
      <FilledButton
        v-if="!globalState.token.value"
        @click="router.push('/login')"
        btnText="Log In"
      />
      <OutlinedButton v-else @click="logout" btnText="Log Out" />
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
$accent-color: #cb0a0a;

.header {
  width: 100%;
  margin: 0 auto;
  padding: 30px 0;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.name {
  width: 320px;
  cursor: pointer;
  margin-left: 10%;
}

.buttons {
  display: flex;
  flex-direction: row;
  gap: 8px;
  margin-right: 10%;
  justify-self: flex-end;
}
</style>
