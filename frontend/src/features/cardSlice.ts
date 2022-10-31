import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import Axios from "axios";

interface CardConfig {
  card: {
    title: String;
    name: String;
  };

  status: "idle" | "loading" | "success" | "failed";
}

const initialState: CardConfig = {
  card: {
    title: "",
    name: "",
  },
  status: "idle",
};

export const getCardInfo = createAsyncThunk(
  "card/getCardInfo",
  async (params: String, { rejectWithValue }) => {
    try {
      const response = await Axios.get("/", { params });
      return response.data;
    } catch (err) {
      return rejectWithValue(err);
    }
  }
);

const CardSlice = createSlice({
  name: "card",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(getCardInfo.pending, (state) => {
        state.status = "loading";
      })
      .addCase(getCardInfo.fulfilled, (state, { payload }) => {
        state.status = "success";
        state.card = payload;
      })
      .addCase(getCardInfo.rejected, (state) => {
        state.status = "failed";
      });
  },
});

export default CardSlice.reducer;
