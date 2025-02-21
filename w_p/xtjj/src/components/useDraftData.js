import { ref, onMounted } from 'vue';
import { getInfo } from './getInfo';

export function useUserData(apiUrl) {
  const userdata = ref({});
  const loading = ref(false);
  const error = ref(null);
  const { user_id, token } = getInfo();

  const fetchDatas = async () => {
    loading.value = true;
    try {
      const response = await fetch(apiUrl, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'token': token
        }
      });

      // 检查响应状态
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      // 检查 Content-Type 头
      const contentType = response.headers.get('Content-Type');
      if (!contentType || !contentType.includes('application/json')) {
        const responseText = await response.text();
        throw new Error(`Expected JSON response, but got ${contentType}. Response text: ${responseText}`);
      }

      // 解析 JSON 数据
      const data = await response.json();
      console.log(data);
      userdata.value = data.data;
    } catch (err) {
      console.error(err);
      error.value = err;
    } finally {
      loading.value = false;
    }
  };

  onMounted(() => {
    fetchDatas();
  });

  return {
    userdata,
    loading,
    error
  };
}