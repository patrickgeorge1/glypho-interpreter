JFLAGS = -g -d
JC = javac
JVM = java

all: build

sources = $(wildcard ./com/company/*.java)
classes = $(sources:.java=.class)

test:
	$(JC) $(JFLAGS) ./classes $(sources); \
	cd classes; \
	pwd; \
	jar -cvfm tema.jar Manifest.txt com/company/*.class
	java -jar tema.jar

pa:
	$(JC) $(JFLAGS) ./classes $(sources); \
	cd classes; \
	jar -cvfm tema.jar Manifest.txt com/company/*.class; \
	cd ..; \
	java -jar classes/tema.jar $(input) $(base)



build:
	$(JC) $(JFLAGS) ./classes $(sources); \
	cd classes; \
	jar -cvfm tema.jar Manifest.txt com/company/*.class



run:
	java -jar classes/tema.jar $(input) $(base)

.PHONY: clean
clean :
	rm -rf classes/com classes/tema.jar