package org.jsoup.parser;

import org.jsoup.helper.DescendableLinkedList;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author Jonathan Hedley
 */
abstract class TreeBuilder {
    org.jsoup.parser.CharacterReader reader;
    Tokeniser tokeniser;
    protected Document doc; // current doc we are building into
    protected DescendableLinkedList<Element> stack; // the stack of open elements
    protected String baseUri; // current base uri, for creating new elements
    protected org.jsoup.parser.Token currentToken; // currentToken is used only for error tracking.
    protected org.jsoup.parser.ParseErrorList errors; // null when not tracking errors

    protected void initialiseParse(String input, String baseUri, org.jsoup.parser.ParseErrorList errors) {
        Validate.notNull(input, "String input must not be null");
        Validate.notNull(baseUri, "BaseURI must not be null");

        doc = new Document(baseUri);
        reader = new org.jsoup.parser.CharacterReader(input);
        this.errors = errors;
        tokeniser = new Tokeniser(reader, errors);
        stack = new DescendableLinkedList<Element>();
        this.baseUri = baseUri;
    }

    Document parse(String input, String baseUri) {
        return parse(input, baseUri, org.jsoup.parser.ParseErrorList.noTracking());
    }

    Document parse(String input, String baseUri, org.jsoup.parser.ParseErrorList errors) {
        initialiseParse(input, baseUri, errors);
        runParser();
        return doc;
    }

    protected void runParser() {
        while (true) {
            org.jsoup.parser.Token token = tokeniser.read();
            process(token);

            if (token.type == org.jsoup.parser.Token.TokenType.EOF)
                break;
        }
    }

    protected abstract boolean process(org.jsoup.parser.Token token);

    protected Element currentElement() {
        return stack.getLast();
    }
}
