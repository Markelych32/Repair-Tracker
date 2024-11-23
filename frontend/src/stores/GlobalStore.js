import { createGlobalState, useStorage } from '@vueuse/core'

export const useGlobalState = createGlobalState(() => {
  const token = useStorage('token', null)
  const userId = useStorage('userId', null)

  return {
    token,
    userId,
    setToken(newToken) {
      token.value = newToken
    },
    clearToken() {
      token.value = null
    },
    setUserId(newUserId) {
      userId.value = newUserId
    },
    clearUserId() {
      userId.value = null
    },
    clearUser() {
      token.value = null
      userId.value = null
    },
  }
})
