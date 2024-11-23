import AXIOS from '../axios.js'
import { defineStore } from 'pinia'
import { useGlobalState } from './GlobalStore.js'

const globalState = useGlobalState()

export const useTaskStore = defineStore('TaskStore', {
  state: () => {
    return {
      tasks: [],
    }
  },

  actions: {
    async fetchData() {
      try {
        const response = await AXIOS.get(
          `/users/${globalState.userId.value}/tasks`,
          {
            headers: {
              Authorization: `Bearer ${globalState.token.value}`,
            },
          }
        )

        this.tasks = response.data.tasks
      } catch (e) {}
    },
    async sendData(id, title, payload, isDone) {
      await AXIOS.put(
        '/tasks',
        {
          user_id: Number(globalState.userId.value),
          task_id: id,
          title: title,
          payload: payload,
          is_done: isDone,
        },
        {
          headers: {
            Authorization: `Bearer ${globalState.token.value}`,
          },
        }
      )
    },
    addNewTask(id) {
      this.tasks.push({
        id: id,
        title: null,
        payload: null,
        isDone: false,
      })
    },

    async saveNewTask(id, title, payload, isDone) {
      await AXIOS.post(
        '/tasks',
        {
          task_id: id,
          user_id: Number(globalState.userId.value),
          title: title,
          payload: payload,
          isDone: isDone,
        },
        {
          headers: {
            Authorization: `Bearer ${globalState.token.value}`,
          },
        }
      )
    },

    async editTask(id, title, payload, isDone) {
      const target = this.tasks.find((task) => task.id === id)

      if (target) {
        target.title = title
        target.payload = payload
        target.isDone = isDone

        await this.sendData(id, title, payload, isDone)
      }
    },

    async editIsDone(id, isDone) {
      const target = this.tasks.find((task) => task.id === id)
      if (target) {
        target.isDone = isDone

        this.sendData(id, target.title, target.payload, isDone)
      }
    },

    async editTitle(id, title) {
      const target = this.tasks.find((task) => task.id === id)
      if (target) {
        target.title = title

        this.sendData(id, title, target.payload, target.isDone)
      }
    },

    async deleteTask(id) {
      this.tasks = this.tasks.filter((task) => task.id !== id)

      await AXIOS.delete(`/tasks`, {
        headers: {
          Authorization: `Bearer ${globalState.token.value}`,
          taskId: id,
        },
      })
    },
  },

  getters: {
    allTasks(state) {
      return state.tasks || []
    },
    completedTasks(state) {
      return state.tasks.filter((task) => task.is_done) || []
    },
    totalTasks(state) {
      return state.tasks.length
    },
    activeTasks(state) {
      return state.tasks.filter((task) => !task.is_done) || []
    },
  },
})
