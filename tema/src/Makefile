build:
	javac -g  com/company/*.java

run: build
	java com.company.Main $(input) $(base)

.PHONY: clean
clean:
	rm com/company/*.class

sanitaze:
	rm -rf *.err
	rm -rf *.rawerr
	rm -rf *.out
