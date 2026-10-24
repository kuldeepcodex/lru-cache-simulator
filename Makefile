BUILD := build

all:
	@mkdir -p $(BUILD)
	javac -Xlint:all -d $(BUILD) src/*.java tests/*.java

run: all
	java -cp $(BUILD) Simulation

test: all
	java -cp $(BUILD) LRUCacheTest

clean:
	rm -rf $(BUILD)

.PHONY: all run test clean
