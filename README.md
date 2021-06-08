# SALC
Sistema de atención legal al consumidor

## INSTALACIÓN Y USO
#### GENERICO:

1. Lanzar Elasticsearch y Kibana

#### USER MODEL:

1. Cargar el indice de usuarios del archivo ```index-test-elasticsearch.json```
2. Cargar los 3 tipos de usuarios.

#### CASE MODEL:

1. Crear el job ```cases``` en [fscrawler](https://fscrawler.readthedocs.io/en/latest/) de la siguiente manera: ```.\fscrawler.bat cases```
2. Modificar el archivo ```_settings.yaml``` que se encuentra en ```~\.fscrawler\cases\``` y cambiar la ruta de "url" por el directorio en el que se encuentran los archivos PDF, y cambiar el _update_rate_ a ```update_rate: "1s"```.
3. Modificar el archivo ```_settings.json``` que se encuentra en ```~\.fscrawler\_default\7\``` y agregar al mapping customizado.
```json
"info": {
	"properties": {
	  "date": {
		"type": "date",
		"format": "dd/MM/yyyy"
	  },
	  "court": {
		"type": "text"
	  },
	  "jurisdiction": {
		"type": "text"
	  },
	  "region": {
		"type": "text"
	  },
	  "summary": {
		"type": "text"
	  },
	  "tags": {
		"type": "nested",
		"properties": {
		  "tag": {
			"type": "text"
		  }
		}
	  },
	  "case_number": {
		"type": "text"
	  }
	}
}
```
5. Ejecutar nuevamente el comando ```.\fscrawler.bat cases```

#### ACTUALIZACIÓN DE CASES:

1. Realizar la petición ```addCase``` secuencialmente para actualizar la información de los casos existentes (metidos con _fscrawler_).
