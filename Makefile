.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
DOCDIR=docs
JAVAC=/usr/bin/javac
JAVA=/usr/bin/java
JAVADOC=/usr/bin/javadoc

$(BINDIR)/%.class: $(SRCDIR)/%.java
	 $(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES2=Score.class ScoreUpdater.class WordDictionary.class FallingWord.class WordMover.class HungryWord.class \
 	HungryWordMover.class GamePanel.class \
	CatchWord.class TypingTutorApp.class

CLASSES=$(CLASSES2:%.class=$(BINDIR)/%.class)

default: $(CLASSES)

# a run command to use with make "make run"
run: $(CLASSES)
	$(JAVA) -cp $(BINDIR) TypingTutorApp
	
# to clean class files after the compilation is complete.
clean:
	rm $(BINDIR)/*.class
	rm -r $(DOCDIR)/*

# document the class using their javadocs
doc:
	$(JAVADOC) -d $(DOCDIR) $(SRCDIR)/*.java