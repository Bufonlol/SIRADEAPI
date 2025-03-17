import sys
import numpy as np
import joblib
import librosa

def extraer_caracteristicas(ruta_audio, n_mfcc=13):
    y, sr = librosa.load(ruta_audio, sr=None)
    mfccs = librosa.feature.mfcc(y=y, sr=sr, n_mfcc=n_mfcc)
    return np.mean(mfccs, axis=1)

def predecir_audio(ruta_audio):
    modelo = joblib.load("modelo_svc.pkl")
    scaler = joblib.load("scaler.pkl")
    encoder = joblib.load("encoder.pkl")

    caracteristicas = extraer_caracteristicas(ruta_audio).reshape(1, -1)
    caracteristicas = scaler.transform(caracteristicas)
    prediccion = modelo.predict(caracteristicas)
    probabilidad = modelo.predict_proba(caracteristicas)[0]
    etiqueta = encoder.inverse_transform(prediccion)[0]
    probabilidad_etiqueta = probabilidad[prediccion[0]]  # Probabilidad asociada a la predicción

    # Imprimir el resultado en formato: etiqueta,probabilidad
    print(f"{etiqueta},{probabilidad_etiqueta}")

if __name__ == "__main__":
    ruta_audio = sys.argv[1]
    predecir_audio(ruta_audio)
