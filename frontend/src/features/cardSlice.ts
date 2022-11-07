import { RootState } from "./../app/store";
import { client } from "./../app/Axios";
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

export interface RequestParams {
  userId: string;
  cardId: number;
}

export interface CardConfig {
  imageUrl: string;
  status: "idle" | "loading" | "success" | "failed";
}

const initialState: CardConfig = {
  imageUrl:
    "https://firebasestorage.googleapis.com/v0/b/naya-365407.appspot.com/o/naya%2FOxkibXM9LhYHtBAoLYaB7nQnF4s1%2FNAYA-MEDIA-1667740225913.png?alt=media&token=40dbfebb-3ce4-4872-8b9b-068723c12d62",
  status: "idle",
};

export const getCardInfo = createAsyncThunk(
  "card/getCardInfo",
  async (params: RequestParams, { rejectWithValue }) => {
    try {
      const response = await client.get("/", { params });
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
