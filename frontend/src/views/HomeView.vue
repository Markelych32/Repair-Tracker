<template>
  <div class="home">
    <Header />
    <TasksList />
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue'
import TasksList from '@/components/TasksList.vue'
</script>

<style lang="scss" scoped>
$main-bg-color: #17171a;
$elem-bg-color: #3e4045;
$accent-color: #cb0a0a;

.home {
  max-width: 80%;
  min-height: 100vh;
  margin: 0 auto;
}
</style>
