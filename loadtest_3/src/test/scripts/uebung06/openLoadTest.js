import http from 'k6/http';
import { sleep } from 'k6';


import {BASE_URL} from "../internals/config.js";
import {getRandomStore, getRandomBook} from "../internals/data.js";

export const options = {};

export default function () {
}
