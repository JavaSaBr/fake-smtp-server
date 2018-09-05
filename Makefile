all:
	./gradlew build
env:
	docker-compose up --force-recreate --build
nt:
	./gradlew build -x test

