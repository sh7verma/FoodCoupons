package app.com.foodcoupons.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by app on 17-Nov-17.
 */

public class GooglePlaceModal {


    private String status;
    private List<PredictionsBean> predictions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PredictionsBean> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionsBean> predictions) {
        this.predictions = predictions;
    }

    public static class PredictionsBean implements Parcelable {


        public static final Creator<PredictionsBean> CREATOR = new Creator<PredictionsBean>() {
            @Override
            public PredictionsBean createFromParcel(Parcel source) {
                return new PredictionsBean(source);
            }

            @Override
            public PredictionsBean[] newArray(int size) {
                return new PredictionsBean[size];
            }
        };
        private String description;
        private String id;
        private String place_id;
        private String reference;
        private StructuredFormattingBean structured_formatting;
        private List<MatchedSubstringsBean> matched_substrings;
        private List<TermsBean> terms;
        private List<String> types;

        public PredictionsBean() {
        }

        protected PredictionsBean(Parcel in) {
            this.description = in.readString();
            this.id = in.readString();
            this.place_id = in.readString();
            this.reference = in.readString();
            this.structured_formatting = in.readParcelable(StructuredFormattingBean.class.getClassLoader());
            this.matched_substrings = in.createTypedArrayList(MatchedSubstringsBean.CREATOR);
            this.terms = in.createTypedArrayList(TermsBean.CREATOR);
            this.types = in.createStringArrayList();
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public StructuredFormattingBean getStructured_formatting() {
            return structured_formatting;
        }

        public void setStructured_formatting(StructuredFormattingBean structured_formatting) {
            this.structured_formatting = structured_formatting;
        }

        public List<MatchedSubstringsBean> getMatched_substrings() {
            return matched_substrings;
        }

        public void setMatched_substrings(List<MatchedSubstringsBean> matched_substrings) {
            this.matched_substrings = matched_substrings;
        }

        public List<TermsBean> getTerms() {
            return terms;
        }

        public void setTerms(List<TermsBean> terms) {
            this.terms = terms;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.description);
            dest.writeString(this.id);
            dest.writeString(this.place_id);
            dest.writeString(this.reference);
            dest.writeParcelable(this.structured_formatting, flags);
            dest.writeTypedList(this.matched_substrings);
            dest.writeTypedList(this.terms);
            dest.writeStringList(this.types);
        }

        public static class StructuredFormattingBean implements Parcelable {


            public static final Creator<StructuredFormattingBean> CREATOR = new Creator<StructuredFormattingBean>() {
                @Override
                public StructuredFormattingBean createFromParcel(Parcel source) {
                    return new StructuredFormattingBean(source);
                }

                @Override
                public StructuredFormattingBean[] newArray(int size) {
                    return new StructuredFormattingBean[size];
                }
            };
            private String main_text;
            private String secondary_text;
            private List<MainTextMatchedSubstringsBean> main_text_matched_substrings;

            public StructuredFormattingBean() {
            }

            protected StructuredFormattingBean(Parcel in) {
                this.main_text = in.readString();
                this.secondary_text = in.readString();
                this.main_text_matched_substrings = new ArrayList<MainTextMatchedSubstringsBean>();
                in.readList(this.main_text_matched_substrings, MainTextMatchedSubstringsBean.class.getClassLoader());
            }

            public String getMain_text() {
                return main_text;
            }

            public void setMain_text(String main_text) {
                this.main_text = main_text;
            }

            public String getSecondary_text() {
                return secondary_text;
            }

            public void setSecondary_text(String secondary_text) {
                this.secondary_text = secondary_text;
            }

            public List<MainTextMatchedSubstringsBean> getMain_text_matched_substrings() {
                return main_text_matched_substrings;
            }

            public void setMain_text_matched_substrings(List<MainTextMatchedSubstringsBean> main_text_matched_substrings) {
                this.main_text_matched_substrings = main_text_matched_substrings;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.main_text);
                dest.writeString(this.secondary_text);
                dest.writeList(this.main_text_matched_substrings);
            }

            public static class MainTextMatchedSubstringsBean implements Parcelable {

                public static final Creator<MainTextMatchedSubstringsBean> CREATOR = new Creator<MainTextMatchedSubstringsBean>() {
                    @Override
                    public MainTextMatchedSubstringsBean createFromParcel(Parcel source) {
                        return new MainTextMatchedSubstringsBean(source);
                    }

                    @Override
                    public MainTextMatchedSubstringsBean[] newArray(int size) {
                        return new MainTextMatchedSubstringsBean[size];
                    }
                };
                private int length;
                private int offset;

                public MainTextMatchedSubstringsBean() {
                }

                protected MainTextMatchedSubstringsBean(Parcel in) {
                    this.length = in.readInt();
                    this.offset = in.readInt();
                }

                public int getLength() {
                    return length;
                }

                public void setLength(int length) {
                    this.length = length;
                }

                public int getOffset() {
                    return offset;
                }

                public void setOffset(int offset) {
                    this.offset = offset;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.length);
                    dest.writeInt(this.offset);
                }
            }
        }

        public static class MatchedSubstringsBean implements Parcelable {

            public static final Creator<MatchedSubstringsBean> CREATOR = new Creator<MatchedSubstringsBean>() {
                @Override
                public MatchedSubstringsBean createFromParcel(Parcel source) {
                    return new MatchedSubstringsBean(source);
                }

                @Override
                public MatchedSubstringsBean[] newArray(int size) {
                    return new MatchedSubstringsBean[size];
                }
            };
            private int length;
            private int offset;

            public MatchedSubstringsBean() {
            }

            protected MatchedSubstringsBean(Parcel in) {
                this.length = in.readInt();
                this.offset = in.readInt();
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.length);
                dest.writeInt(this.offset);
            }
        }

        public static class TermsBean implements Parcelable {


            public static final Creator<TermsBean> CREATOR = new Creator<TermsBean>() {
                @Override
                public TermsBean createFromParcel(Parcel source) {
                    return new TermsBean(source);
                }

                @Override
                public TermsBean[] newArray(int size) {
                    return new TermsBean[size];
                }
            };
            private int offset;
            private String value;

            public TermsBean() {
            }

            protected TermsBean(Parcel in) {
                this.offset = in.readInt();
                this.value = in.readString();
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.offset);
                dest.writeString(this.value);
            }
        }
    }
}
