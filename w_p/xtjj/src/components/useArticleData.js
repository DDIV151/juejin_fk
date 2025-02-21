import { ref, onMounted } from 'vue';
import { getInfo } from './getInfo';

export function useArticleData(apiUrl) {
  const articles = ref([]);
  const loading = ref(false);
  const error = ref(null);

  const fetchArticles = async () => {
    const {user_id,token}=getInfo();
    loading.value = true;
    try {
      const response = await fetch(apiUrl,{
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'token': token
        }
      });
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      articles.value = data.data;
      console.log(data);
    } catch (err) {
      error.value = err;
    } finally {
      loading.value = false;
    }
  };

  onMounted(() => {
    fetchArticles();
  });

  return {
    articles,
    loading,
    error
  };
}