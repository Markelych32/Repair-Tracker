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

        <FilledButton btn-text="New task ＋" @click="addNewTask()" />
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

const taskStore = useTaskStore()
const globalState = useGlobalState()
const addNewTask = (id = uuidv4()) => taskStore.addNewTask(id)

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

onMounted(() => {
  taskStore.fetchData()
})
</script>

<style lang="scss" scoped>
$main-bg-color: #17171a;
$elem-bg-color: #3e4045;
$accent-color: #cb0a0a;

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
  margin: 20px 0;
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
