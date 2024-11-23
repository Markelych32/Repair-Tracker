<template>
  <div class="login">
    <Header />
    <form>
      <div>
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      <div>
        <label for="password">Пароль:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <FilledButton @click="submit" btnText="Log In" />
    </form>
    <p v-if="errorMessage" style="color: red">{{ errorMessage }}</p>
  </div>
</template>

<script setup>
import AXIOS from '@/axios'
import FilledButton from '@/components/FilledButton.vue'
import Header from '@/components/Header.vue'
import router from '@/router'
import { useGlobalState } from '@/stores/GlobalStore'

import { ref } from 'vue'

const email = ref('')
const password = ref('')
const errorMessage = ref('')
const globalState = useGlobalState()

const submit = async () => {
  const url = 'http://localhost:8081/auth/login'
  const userData = {
    email: email.value,
    password: password.value,
  }
  try {
    const response = await AXIOS.post(url, userData)

    globalState.setToken(response.data.token)
    globalState.setUserId(response.data.user_id)
    router.push('/')
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error)
  }
}
</script>

<style scoped lang="scss">
$main-bg-color: #17171a;
$elem-bg-color: #3e4045;
$accent-color: #eab629;

form {
  max-width: 300px;
  margin: auto;
}
input {
  color: $accent-color;
  font-size: 20px;
  font-weight: 500;
  font-family: Gilroy;
  background-color: transparent;
  border: 2px solid $accent-color;
  border-radius: 5px;
  padding: 12px;

  &:focus {
    outline: none;
    box-shadow: 0px 2px 8px #212121;
  }
}
</style>
