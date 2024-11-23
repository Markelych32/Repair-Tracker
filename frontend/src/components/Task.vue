<template>
  <div class="task" @click="popupShowing = !popupShowing">
    <h3
      v-if="props.task.title && !editing"
      @click.stop="editing = !editing"
      class="title"
    >
      {{ props.task.title }}
    </h3>
    <input
      v-else
      type="text"
      class="input"
      @click.stop
      id="title"
      placeholder="Title"
      :value="props.task.title"
      @keyup.enter="editing = !editing"
      @change="editTitle()"
    />
    <div class="checkbox" v-if="!editing">
      <input
        class="checkbox-input"
        type="checkbox"
        id="done"
        :checked="props.task.is_done"
        v-model="isDone"
        @click.stop
        @change="editIsDone(props.task.id, isDone)"
      />
      <label class="checkbox-label" for="done"></label>
    </div>
    <button @click.stop="deleteTask()" class="delete">
      <img src="../assets/delete.png" alt="" />
    </button>
    <div class="task-popup" v-if="popupShowing && !editing">
      <div @click.stop class="edit-form">
        <h2>Task editing</h2>
        <input
          type="text"
          class="input"
          id="title-edit"
          :value="props.task.title"
          placeholder="Title"
        />
        <textarea
          type="text"
          class="payload-edit input"
          id="payload-edit"
          :value="props.task.payload"
          placeholder="Description"
        />
        <div class="checkbox">
          <label class="checkbox-label" for="done-edit">Done</label>
          <input
            class="checkbox-input"
            type="checkbox"
            id="done-edit"
            :checked="isDone"
          />
        </div>
        <div class="buttons">
          <OutlinedButton
            @click="popupShowing = !popupShowing"
            btnText="Cancel"
          />
          <FilledButton @click="editTask()" btnText="Save" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useTaskStore } from '@/stores/TaskStore'
import { ref } from 'vue'
import FilledButton from './FilledButton.vue'
import OutlinedButton from './OutlinedButton.vue'

const props = defineProps({
  task: Object,
})
const taskStore = useTaskStore()
const isDone = ref(props.task.is_done)
const editing = ref(false)
const popupShowing = ref(false)

const deleteTask = (id = props.task.id) => {
  taskStore.deleteTask(id)
}

const editIsDone = (id = props.task.id, isDone = this.isDone) => {
  taskStore.editIsDone(id, isDone)
}

const editTitle = async (
  id = props.task.id,
  title = document.getElementById('title')?.value,
  payload = document.getElementById('payload-edit')?.value,
  isDone = document.getElementById('done-edit')?.checked
) => {
  if (props.task.title === null && title !== null) {
    await taskStore.saveNewTask(id, title, payload, isDone)
  }

  taskStore.editTitle(id, title)
  editing.value = !editing.value
}

const editTask = (
  id = props.task.id,
  title = document.getElementById('title-edit').value,
  payload = document.getElementById('payload-edit').value,
  is_done = document.getElementById('done-edit').checked
) => {
  taskStore.editTask(id, title, payload, is_done)
  popupShowing.value = !popupShowing.value
  isDone.value = is_done
}
</script>

<style lang="scss" scoped>
$main-bg-color: #17171a;
$elem-bg-color: #3e4045;
$accent-color: #cb0a0a;

.task {
  background-color: $elem-bg-color;
  flex-direction: row;
  border-radius: 10px;
  align-items: center;
  position: relative;
  display: flex;
  padding: 30px;
  margin: 15px 0;
  gap: 20px;

  &:hover .delete {
    opacity: 1;
  }
}

h3 {
  color: $accent-color;
  font-size: 20px;
  font-weight: 500;
  font-family: Gilroy;
}

h2 {
  color: $accent-color;
  font-size: 28px;
  font-weight: 600;
  font-family: Gilroy;
  margin-bottom: 20px;
}
.delete {
  z-index: 1;
  justify-self: flex-end;
  background-color: transparent;
  border: none;
  margin-left: auto;
  opacity: 0;
  cursor: pointer;
  transition: opacity 0.2s;

  img {
    width: 28px;
  }
}

.checkbox {
  width: fit-content;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

.checkbox-input {
  appearance: none;
  position: relative;
  width: 35px;
  height: 35px;
  margin-left: 5px;
  background: transparent;
  box-shadow: inset 0 0 5px rgb(0 0 0 / 0.2);
  border-radius: 10px;
  border: 2px solid #ffffff;
  transition: 0.2s;
  cursor: pointer;
}

.checkbox-label {
  color: $accent-color;
  font-size: 20px;
  font-weight: 500;
  font-family: Gilroy;
}

.checkbox-input::after {
  content: '';
  position: absolute;
  width: 0px;
  height: 0px;
  background-image: url("data:image/svg+xml;charset=UTF-8,%3c!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' 'http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd'%3e%3c!-- Uploaded to: SVG Repo, www.svgrepo.com, Transformed by: SVG Repo Mixer Tools --%3e%3csvg width='32px' height='32px' viewBox='0 0 24 24' fill='none' xmlns='http://www.w3.org/2000/svg'%3e%3cg id='SVGRepo_bgCarrier' stroke-width='0'/%3e%3cg id='SVGRepo_tracerCarrier' stroke-linecap='round' stroke-linejoin='round'/%3e%3cg id='SVGRepo_iconCarrier'%3e%3cpath d='M4 12.6111L8.92308 17.5L20 6.5' stroke='%23ffffff' stroke-width='3' stroke-linecap='round' stroke-linejoin='round'/%3e%3c/g%3e%3c/svg%3e");
  background-repeat: no-repeat;
  transition: 200ms;
}

.checkbox-input:checked::after {
  width: 35px;
  height: 35px;
  transition: 200ms;
}

.checkbox-input:checked {
  background-color: $accent-color;
}

.task-popup {
  position: fixed;
  width: 100vw;
  height: 100vh;
  inset: 0px 0px;
  background-color: #00000065;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
}

.edit-form {
  width: 80%;
  border-radius: 10px;
  padding: 6%;
  background-color: $elem-bg-color;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.input {
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

.buttons {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 8px;
  justify-content: flex-end;
}
.payload-edit {
  resize: vertical;
  min-height: 60px;
  max-height: 250px;
}
</style>
