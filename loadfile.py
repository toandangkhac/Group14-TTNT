import pandas as pd
# import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.svm import SVC
from sklearn.metrics import classification_report
import pickle
filename = 'train_history.pickle'

hotelCancedData = pd.read_csv(
    "C:\\Users\\toand\\Desktop\\backup\\HKI-2022-2023\\AI\\Group14\\GitHub\\Group14-TTNT\\Hotel-Reservations.csv")
hotelCancedData = hotelCancedData.replace({'type_of_meal_plan': {
                                          'Meal Plan 1': 0, 'Meal Plan 2': 1, 'Meal Plan 3': 2, 'Not Selected': 3}})
hotelCancedData = hotelCancedData.replace({
    'room_type_reserved':
    {'Room_Type 1': 0, 'Room_Type 2': 1, 'Room_Type 3': 2, 'Room_Type 4': 3,
        'Room_Type 5': 4, 'Room_Type 6': 5, 'Room_Type 7': 6}
})
hotelCancedData = hotelCancedData.replace({
    'market_segment_type':
    {'Aviation': 0, 'Complementary': 1, 'Corporate': 2, 'Offline': 3, 'Online': 4}
})
hotelCancedData = hotelCancedData.replace({
    'booking_status':
    {'Not_Canceled': 0, 'Canceled': 1}
})


X = hotelCancedData.drop(['booking_status', 'Booking_ID'], axis=1)

Y = hotelCancedData['booking_status']  #nhãn của tập dữ liệu, được sử dụng để đánh giá mô hình

X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.2, random_state=42)  # 20% dùng để kiểm tra, 80% dùng để huấn luyện


filename = 'train_history.pickle'
load_model = pickle.load(open(filename, 'rb'))
print(load_model)
y_pred = load_model.predict(X_test)
print(y_pred)
print(classification_report(Y_test, y_pred))



# precision được tính bằng số true positives chia cho tổng số điểm được dự đoán là positive (true positives + false positives).

# Recall là tỷ lệ giữa số điểm dữ liệu được phân loại đúng vào lớp positive và tổng số điểm thực tế thuộc lớp positive.
# Recall = true positives / (true positives + false negatives)

# F1-score là trung bình điều hòa giữa precision và recall. F1-score càng cao, thì hiệu suất phân loại của mô hình càng tốt.

# Support là số lượng điểm dữ liệu thuộc vào từng lớp.
# Support = true positives + false negatives (tương đương với tổng số điểm dữ liệu thực tế thuộc lớp positive)

# Accuracy: là tỷ lệ giữa số điểm dữ liệu được phân loại đúng và tổng số điểm dữ liệu.
# Accuracy = (true positives + true negatives) / (true positives + false positives + true negatives + false negatives)
# Macro average: là trung bình của các chỉ số precision, recall và F1-score được tính trên từng lớp phân loại, sau đó lấy trung bình của các giá trị đó. 
# Macro average đánh giá hiệu suất của mô hình phân loại trên mỗi lớp một cách đồng đều.
# Macro average = (precision_1 + precision_2 + ... + precision_k) / k

# Weighted average: là trung bình của các chỉ số precision, recall và F1-score được tính trên từng lớp phân loại, sau đó lấy trung bình của các giá trị đó dựa trên tỉ lệ số lượng điểm dữ liệu thuộc vào từng lớp.
# Weighted average đánh giá hiệu suất của mô hình phân loại dựa trên số lượng điểm dữ liệu thuộc vào từng lớp (support).
# Weighted average = (precision_1 * support_1 + precision_2 * support_2 + ... + precision_k * support_k) / N