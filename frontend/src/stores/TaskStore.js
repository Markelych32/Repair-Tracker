import AXIOS from '../axios.js'
import { defineStore } from 'pinia'

export const useTaskStore = defineStore('TaskStore', {
  state: () => {
    return {
      tasks: [
        {
          id: 0,
          title: '',
          payload: '',
          isDone: false,
        },
        {
          id: 1,
          title: 'test2',
          payload: '',
          isDone: false,
        },
        {
          id: 2,
          title: 'test3',
          payload: '',
          isDone: false,
        },
      ],
    }
  },

  actions: {
    async fetchData() {
      const response = await AXIOS.get(
        'http://localhost:8081/api/v1/users/' +
          window.localStorage.getItem('userId')
      )
      this.tasks = response.data
    },
    sendData() {
      AXIOS.post(
        'http://localhost:8081/api/v1/users/' +
          window.localStorage.getItem('userId'),
        this.tasks
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
    editTask(id, title, payload, isDone) {
      const target = this.tasks.find((task) => task.id === id)
      target.title = title
      target.payload = payload
      target.isDone = isDone
    },
    editIsDone(id, isDone) {
      const target = this.tasks.find((task) => task.id === id)
      if (target) target.isDone = isDone
    },
    editTitle(id, title) {
      const target = this.tasks.find((task) => task.id === id)
      if (target) target.title = title
    },
    deleteTask(id) {
      this.tasks = this.tasks.filter((task) => task.id !== id)
    },
  },

  getters: {
    allTasks(state) {
      return state.tasks
    },
    completedTasks(state) {
      return state.tasks.filter((task) => task.isDone)
    },
    totalTasks(state) {
      return state.tasks.length
    },
    activeTasks(state) {
      return state.tasks.filter((task) => !task.isDone)
    },
  },
})
