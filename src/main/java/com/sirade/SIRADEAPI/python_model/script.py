#!/usr/bin/env python3
import sys
import numpy as np
import joblib
import librosa

def extraer_caracteristicas(ruta_audio, n_mfcc=13):
    # Cargar el audio y extraer los MFCCs
    y, sr = librosa.load(ruta_audio, sr=None)
    mfccs = librosa.feature.mfcc(y=y, sr=sr, n_mfcc=n_mfcc)
    return np.mean(mfccs, axis=1)

def predecir_audio(ruta_audio):
    # Cargar modelo, escalador y encoder
    modelo = joblib.load("modelo_svc.pkl")
    scaler = joblib.load("scaler.pkl")
    encoder = joblib.load("encoder.pkl")

    # Extraer características y realizar la predicción
    caracteristicas = extraer_caracteristicas(ruta_audio).reshape(1, -1)
    caracteristicas = scaler.transform(caracteristicas)
    prediccion = modelo.predict(caracteristicas)
    probabilidad = modelo.predict_proba(caracteristicas)[0]
    etiqueta = encoder.inverse_transform(prediccion)[0]
    probabilidad_etiqueta = probabilidad[prediccion[0]]  # Probabilidad asociada

    # Imprimir el resultado en el formato esperado: "etiqueta,probabilidad"
    print(f"{etiqueta},{probabilidad_etiqueta}", flush=True)

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("error: Debe proporcionar la ruta del archivo de audio", flush=True)
        sys.exit(1)

    ruta_audio = sys.argv[1]
    predecir_audio(ruta_audio)
