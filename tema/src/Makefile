build:
	javac com/company/*.java

run: build
	java com.company.Main $(input) $(base)

clean:
	rm com/company/*.class

sanitaze:
	rm -rf *.err
	rm -rf *.rawerr
	rm -rf *.out