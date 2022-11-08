import { RootState } from "./../app/store";
import { client } from "./../app/Axios";
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

export interface RequestParams {
  userId: string;
  sendCardId: number;
}

export interface CardConfig {
  imageUrl: string;
  status: "idle" | "loading" | "success" | "failed";
}

const initialState: CardConfig = {
  imageUrl: "",
  status: "idle",
};

export const getCardInfo = createAsyncThunk(
  "card/getCardInfo",
  async (params: RequestParams, { rejectWithValue }) => {
    console.log(params);
    try {
      const response = await client.get(`/sendCard`, { params });
      console.log(response);
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
        state.imageUrl = payload;
      })
      .addCase(getCardInfo.rejected, (state) => {
        state.status = "failed";
      });
  },
});

export default CardSlice.reducer;

export const selectImageUrl = (state: RootState) => state.card.imageUrl;
