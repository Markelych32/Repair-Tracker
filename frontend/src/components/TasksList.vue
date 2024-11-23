<template>
  <div v-if="authorized" class="tasks-list">
    <div class="header">
      <h2>Tasks</h2>
      <div class="tasks-mode">
        <div class="radio-input">
          <label for="mode1">All tasks</label>
          <input type="radio" class="radio" id="mode1" name="mode" checked />
        </div>
        <div class="radio-input">
          <label for="mode1">Active tasks</label>
          <input type="radio" class="radio" id="mode2" name="mode" />
        </div>
        <div class="radio-input">
          <label for="mode1">Completed tasks</label>
          <input type="radio" class="radio" id="mode3" name="mode" />
        </div>
      </div>

      <FilledButton btn-text="New task ＋" @click="addNewTask()" />
    </div>
    <div class="list" v-for="task in tasks" :key="task.id">
      <Task :task="task" />
    </div>
  </div>
</template>

<script setup>
import Task from './Task.vue'
import FilledButton from './FilledButton.vue'
import { useTaskStore } from '@/stores/TaskStore'
import { storeToRefs } from 'pinia'
import { computed } from 'vue'

const taskStore = useTaskStore()
const { tasks } = storeToRefs(taskStore)
const addNewTask = (id = Date.now()) => taskStore.addNewTask(id)

const authorized = computed(() => {
  return !!window.localStorage.getItem('token')
})
</script>

<style lang="scss" scoped>
$main-bg-color: #17171a;
$elem-bg-color: #3e4045;
$accent-color: #eab629;

.header {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  .radio-input {
    font-weight: 600;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    gap: 8px;

    input[type='radio'] {
      accent-color: $accent-color;
    }
  }
}

.tasks-mode {
  display: flex;
  flex-direction: row;
  gap: 30px;
  align-items: center;
}

h2 {
  margin: 20px 0;
  color: $accent-color;
  font-size: 28px;
}

label {
  color: $accent-color;
  font-size: 16px;
}
</style>
