<template>
  <div class="task" @click="changePopupVisibility()">
    <h3 v-if="!editing" @click.stop="editing = !editing" class="title">
      {{ props.task.title }}
    </h3>

    <select
      v-else
      class="input"
      @click.stop
      id="title"
      v-model="props.task.title"
      @input="editTitle()"
    >
      <option v-for="title in titles.types" :key="title.id" :value="title.name">
        {{ title.name }}
      </option>
    </select>

    <div class="checkbox" v-if="props.task.title !== null">
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

    <button
      @click.stop="deleteTask()"
      class="delete"
      v-if="props.task.title !== null"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        x="0px"
        y="0px"
        width="35"
        height="35"
        viewBox="0,0,256,256"
      >
        <g
          fill="#cb0a0a"
          fill-rule="nonzero"
          stroke="none"
          stroke-width="1"
          stroke-linecap="butt"
          stroke-linejoin="miter"
          stroke-miterlimit="10"
          stroke-dasharray=""
          stroke-dashoffset="0"
          font-family="none"
          font-weight="none"
          font-size="none"
          text-anchor="none"
          style="mix-blend-mode: normal"
        >
          <g transform="scale(5.12,5.12)">
            <path
              d="M21,2c-1.64545,0 -3,1.35455 -3,3v2h-7.8457c-0.05615,-0.00939 -0.113,-0.01396 -0.16992,-0.01367c-0.04844,0.00105 -0.09675,0.00562 -0.14453,0.01367h-1.83984c-0.36064,-0.0051 -0.69608,0.18438 -0.87789,0.49587c-0.18181,0.3115 -0.18181,0.69676 0,1.00825c0.18181,0.3115 0.51725,0.50097 0.87789,0.49587h1v36c0,1.64545 1.35455,3 3,3h26c1.64545,0 3,-1.35455 3,-3v-36h1c0.36064,0.0051 0.69608,-0.18438 0.87789,-0.49587c0.18181,-0.3115 0.18181,-0.69676 0,-1.00825c-0.18181,-0.3115 -0.51725,-0.50097 -0.87789,-0.49587h-1.83203c-0.10799,-0.01785 -0.21818,-0.01785 -0.32617,0h-7.8418v-2c0,-1.64545 -1.35455,-3 -3,-3zM21,4h8c0.55455,0 1,0.44545 1,1v2h-10v-2c0,-0.55455 0.44545,-1 1,-1zM11,9h7.83203c0.10799,0.01785 0.21818,0.01785 0.32617,0h11.67383c0.10799,0.01785 0.21818,0.01785 0.32617,0h7.8418v36c0,0.55454 -0.44546,1 -1,1h-26c-0.55454,0 -1,-0.44546 -1,-1zM18.98438,13.98633c-0.55152,0.00862 -0.99193,0.46214 -0.98437,1.01367v25c-0.0051,0.36064 0.18438,0.69608 0.49587,0.87789c0.3115,0.18181 0.69676,0.18181 1.00825,0c0.3115,-0.18181 0.50097,-0.51725 0.49587,-0.87789v-25c0.0037,-0.2703 -0.10218,-0.53059 -0.29351,-0.72155c-0.19133,-0.19097 -0.45182,-0.29634 -0.72212,-0.29212zM24.98438,13.98633c-0.55152,0.00862 -0.99193,0.46214 -0.98437,1.01367v25c-0.0051,0.36064 0.18438,0.69608 0.49587,0.87789c0.3115,0.18181 0.69676,0.18181 1.00825,0c0.3115,-0.18181 0.50097,-0.51725 0.49587,-0.87789v-25c0.0037,-0.2703 -0.10218,-0.53059 -0.29351,-0.72155c-0.19133,-0.19097 -0.45182,-0.29634 -0.72212,-0.29212zM30.98438,13.98633c-0.55152,0.00862 -0.99193,0.46214 -0.98437,1.01367v25c-0.0051,0.36064 0.18438,0.69608 0.49587,0.87789c0.3115,0.18181 0.69676,0.18181 1.00825,0c0.3115,-0.18181 0.50097,-0.51725 0.49587,-0.87789v-25c0.0037,-0.2703 -0.10218,-0.53059 -0.29351,-0.72155c-0.19133,-0.19097 -0.45182,-0.29634 -0.72212,-0.29212z"
            ></path>
          </g>
        </g>
      </svg>
    </button>

    <div class="task-popup" v-if="props.task.title !== null && popupShowing">
      <div @click.stop class="edit-form">
        <h2>Task editing</h2>
        <select class="input" @click.stop id="title" v-model="props.task.title">
          <option
            v-for="title in titles.types"
            :key="title.id"
            :value="title.name"
          >
            {{ title.name }}
          </option>
        </select>
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
import { ref, onMounted } from 'vue'
import FilledButton from './FilledButton.vue'
import OutlinedButton from './OutlinedButton.vue'
import { useGlobalState } from '@/stores/GlobalStore'
import AXIOS from '../axios.js'

const props = defineProps({
  task: Object,
})
const taskStore = useTaskStore()
const isDone = ref(props.task.is_done)
const editing = ref(false)
const popupShowing = ref(false)
const titles = ref([])

const globalState = useGlobalState()

const changePopupVisibility = () => {
  if (props.task.title) popupShowing.value = !popupShowing.value
}

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
  title = props.task.title,
  payload = document.getElementById('payload-edit').value,
  is_done = document.getElementById('done-edit').checked
) => {
  taskStore.editTask(id, title, payload, is_done)
  popupShowing.value = !popupShowing.value
  isDone.value = is_done
}

const fetchTitles = async () => {
  try {
    const response = await AXIOS.get('/tasks/types', {
      headers: {
        Authorization: `Bearer ${globalState.token.value}`,
      },
    })
    titles.value = response.data
  } catch (e) {
    globalState.showNotification('You are not authorized! Please log in.')
  }
}

onMounted(() => {
  fetchTitles()
})
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
  padding: 25px;
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
  margin: 14.4px 0;
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
  transition: 0.2s;
  border-radius: 5px;
  padding: 3px;

  &:hover {
    background-color: darken($elem-bg-color, 10%);
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
option {
  background-color: $elem-bg-color;
}
</style>
