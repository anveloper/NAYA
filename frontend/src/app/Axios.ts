import axios, { AxiosRequestConfig } from "axios";

const baseURL: string = "https://k7b104.p.ssafy.io/api/naya";

const axiosConfig: AxiosRequestConfig = {
  baseURL: baseURL,
  headers: {
    "Content-Type": "application/json",
  },
};

export const client = axios.create(axiosConfig);
