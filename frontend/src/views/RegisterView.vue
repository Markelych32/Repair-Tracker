<template>
  <div class="register">
    <Header />
    <form>
      <h2>REGISTER</h2>
      <input
        type="text"
        id="firstname"
        v-model="firstname"
        required
        placeholder="FirstName"
      />
      <input
        type="text"
        id="lastname"
        v-model="lastname"
        required
        placeholder="LastName"
      />
      <input
        type="email"
        id="email"
        v-model="email"
        required
        placeholder="Email"
      />
      <input
        type="password"
        id="password"
        v-model="password"
        required
        placeholder="Password"
      />
      <FilledButton
        style="width: 40%; padding: 15px; font-size: 20px; margin-top: 10px"
        @click="submit"
        btnText="Register"
      />
      <p>
        Already have an account?
        <span @click="router.push('/login')">Log in</span>
      </p>
    </form>
    <p v-if="errorMessage" style="color: red">{{ errorMessage }}</p>
  </div>
</template>

<script setup>
import AXIOS from '@/axios'
import FilledButton from '@/components/FilledButton.vue'
import Header from '@/components/Header.vue'
import router from '@/router'
import Notification from '@/components/Notification.vue'
import { useGlobalState } from '@/stores/GlobalStore'
import { ref } from 'vue'

const email = ref('')
const password = ref('')
const firstname = ref('')
const lastname = ref('')
const globalState = useGlobalState()

const submit = async () => {
  const url = 'http://localhost:8081/auth/register'
  const userData = {
    first_name: firstname.value,
    last_name: lastname.value,
    email: email.value,
    password: password.value,
  }

  try {
    const response = await AXIOS.post(url, userData)
    router.push('/login')
  } catch (error) {
    globalState.showNotification(
      'User with this email has already been registered, try again please try again!'
    )
  }
}
</script>

<style scoped lang="scss">
$main-bg-color: #17171a;
$elem-bg-color: #3e4045;
$accent-color: #cb0a0a;

.register {
  min-height: 100vh;
  max-width: 80%;
  margin: 0 auto;
}

p {
  font-size: 16px;
  color: lighten($elem-bg-color, 30%);

  span {
    cursor: pointer;
    color: $accent-color;
  }
}

h2 {
  color: $accent-color;
  font-weight: 600;
  font-size: 32px;
  margin-bottom: 10px;
}

.register {
  min-height: 100vh;
  max-width: 80%;
  margin: 0 auto;
}

p {
  font-size: 16px;
  color: lighten($elem-bg-color, 30%);

  span {
    cursor: pointer;
    color: $accent-color;
  }
}

h2 {
  color: $accent-color;
  font-weight: 600;
  font-size: 32px;
  margin-bottom: 10px;
}

form {
  width: 35%;
  width: 35%;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  margin-top: 5%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  margin-top: 5%;
}

input {
  width: 100%;
  width: 100%;
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
