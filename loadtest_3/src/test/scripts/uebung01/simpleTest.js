import http from 'k6/http';
import { sleep } from 'k6';


import {BASE_URL} from "../internals/config.js";


export default function () {
    http.get(`${BASE_URL}/api/books`);

    sleep(1);
}
