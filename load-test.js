import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
  vus: 100,
  duration: '30s',
};

export default function () {
  // Substitua <URL_DO_SERVICO> pela URL real obtida no passo 1 (ex: http://127.0.0.1:5XXXX)
  http.get('<URL_DO_SERVICO>/health');
  sleep(0.1);
}