<template>
  <div class="button">{{ props.btnText }}</div>
</template>

<script setup>
const props = defineProps({
  btnText: String,
})
</script>

<style lang="scss" scoped>
$accent-color: #cb0a0a;

.button {
  color: $accent-color;
  padding: 10px 12px;
  border: 2px solid $accent-color;
  border-radius: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: 0.2s;
  font-weight: 600;
  text-align: center;

  &:hover {
    transform: scale(0.95);
    box-shadow: 0px 0px 10px #58585a;
  }
}
</style>
