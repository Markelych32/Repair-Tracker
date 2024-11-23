<template>
  <div>
    <div v-if="authorized" class="tasks-list">
      <div class="header">
        <h2>Tasks</h2>
        <div class="tasks-mode">
          <div class="radio-input">
            <input type="radio" id="mode1" name="mode" checked />
            <label
              @click="updateTasks('allTasks')"
              style="
                border-top-left-radius: 8px;
                border-bottom-left-radius: 8px;
              "
              for="mode1"
              >All tasks</label
            >
          </div>
          <div class="radio-input">
            <input type="radio" id="mode2" name="mode" />
            <label @click="updateTasks('activeTasks')" for="mode2"
              >Active tasks</label
            >
          </div>
          <div class="radio-input">
            <input type="radio" id="mode3" name="mode" />
            <label
              @click="updateTasks('completedTasks')"
              style="
                border-top-right-radius: 8px;
                border-bottom-right-radius: 8px;
              "
              for="mode3"
              >Completed tasks</label
            >
          </div>
        </div>

        <div class="new-task">
          <select class="input" id="title" v-model="selectedTitle">
            <option
              v-for="title in titles.types"
              :key="title.id"
              :value="title.name"
            >
              {{ title.name }}
            </option>
          </select>

          <FilledButton btn-text="New task ＋" @click="addNewTask()" />
        </div>
      </div>
      <div class="list" v-for="task in filteredTasks" :key="task.id">
        <Task :task="task" />
      </div>
    </div>
    <div v-else class="unauthorized" @click="router.push('/login')">
      You are not logged in!<br />The content is available only to authorized
      users.
      <img src="../assets/ussr-cat.png" alt="" />
    </div>
  </div>
</template>

<script setup>
import Task from './Task.vue'
import FilledButton from './FilledButton.vue'
import { useTaskStore } from '@/stores/TaskStore'
import { useGlobalState } from '@/stores/GlobalStore'
import router from '@/router'
import { computed, onMounted, ref } from 'vue'
import { v4 as uuidv4 } from 'uuid'
import AXIOS from '../axios.js'

const titles = ref([])
const taskStore = useTaskStore()
const globalState = useGlobalState()
const selectedTitle = ref(titles[0])

const addNewTask = (id = uuidv4()) => {
  taskStore.addNewTask(id, selectedTitle.value)
  taskStore.saveNewTask(id, selectedTitle.value, null, false)
}

const authorized = computed(() => {
  return !!globalState.token.value
})

const currentMode = ref('allTasks')

const filteredTasks = computed(() => {
  return taskStore[currentMode.value]
})

const updateTasks = (mode) => {
  currentMode.value = mode
}

const fetchTitles = async () => {
  try {
    const response = await AXIOS.get('/tasks/types', {
      headers: {
        Authorization: `Bearer ${globalState.token.value}`,
      },
    })

    titles.value = response.data
    selectedTitle.value = titles.value.types[0].name
  } catch (e) {
    globalState.showNotification('You are not authorized! Please log in.')
  }
}

onMounted(() => {
  taskStore.fetchData()
  fetchTitles()
})
</script>

<style lang="scss" scoped>
$main-bg-color: #17171a;
$elem-bg-color: #3e4045;
$accent-color: #cb0a0a;

.new-task {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.input {
  color: $accent-color;
  font-size: 20px;
  font-weight: 500;
  font-family: Gilroy;
  background-color: $elem-bg-color;
  border: 2px solid $accent-color;
  border-radius: 5px;
  padding: 6px;
  margin-right: 3px;
  outline: none;

  &:focus {
    outline: none;
    box-shadow: 0px 2px 8px #212121;
  }
}

option {
  background-color: $elem-bg-color;
  outline: none;
  border: none;
  border-radius: 0px;
  box-shadow: none;
}

.header {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.tasks-mode {
  display: flex;
  flex-direction: row;
  gap: 0px;
  align-items: center;
}

h2 {
  margin: 30px 0;
  color: $accent-color;
  font-size: 28px;
}

.unauthorized {
  display: flex;
  flex-direction: column;
  font-size: 24px;
  font-weight: 800;
  align-items: center;
  text-align: center;
  color: $accent-color;
  width: 80%;
  margin: 3vh auto;
  cursor: pointer;

  img {
    margin-top: 30px;
    width: 45%;
  }
}

.radio-input {
  display: inline-flex;
  align-items: center;
  cursor: pointer;
  font-weight: 600;

  input[type='radio'] {
    display: none;
  }

  label {
    color: lighten($elem-bg-color, 30%);
    font-size: 16px;
    cursor: pointer;
    background-color: $elem-bg-color;
    border: 2px solid lighten($elem-bg-color, 20%);
    padding: 10px 20px;
    transition: border-color 0.3s ease;
  }

  input[type='radio']:checked ~ label {
    background-color: $elem-bg-color;
    border-color: $accent-color;
    color: $accent-color;
  }
}
</style>
