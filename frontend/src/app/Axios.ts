import axios, { AxiosRequestConfig } from "axios";

const baseURL: string = "https://k7b104.p.ssafy.io:8080/naya/api/";

const axiosConfig: AxiosRequestConfig = {
  baseURL: baseURL,
};

export const client = axios.create(axiosConfig);
