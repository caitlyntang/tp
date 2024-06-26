package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.EmailContainsKeywordPredicate;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.PhoneContainsKeywordsPredicate;
import seedu.address.model.patient.TagContainsKeywordPredicate;


/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListPatientCommandParser implements Parser<ListCommand> {
    @Override
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE,
                        CliSyntax.PREFIX_EMAIL, PREFIX_ALIAS);


        List<Predicate<Patient>> predicates = new ArrayList<>();

        // Checks if there is at least one prefix available.
        boolean hasAtLeastOnePrefixPresent = ParserUtil.hasAtLeastOnePrefixPresent(argMultimap, CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL, PREFIX_ALIAS);

        if (!hasAtLeastOnePrefixPresent && argMultimap.getPreamble().isEmpty()) {
            // If there is no prefix specified, then display all records.
            // TODO: Show an error message here.
            return new ListCommand(PREDICATE_SHOW_ALL_PERSONS);
        }

        if (!hasAtLeastOnePrefixPresent && !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE)
            );
        }


        // All these criteria are AND not OR
        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            String nameToSearch = argMultimap.getValue(CliSyntax.PREFIX_NAME).get();
            predicates.add(new NameContainsKeywordsPredicate(Collections.singletonList(nameToSearch)));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_PHONE).isPresent()) {
            String phoneToSearch = argMultimap.getValue(CliSyntax.PREFIX_PHONE).get();
            predicates.add(new PhoneContainsKeywordsPredicate(Collections.singletonList(phoneToSearch)));
        }


        if (argMultimap.getValue(CliSyntax.PREFIX_EMAIL).isPresent()) {
            String emailToSearch = argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get();
            predicates.add(new EmailContainsKeywordPredicate(Collections.singletonList(emailToSearch)));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_ALIAS).isPresent()) {
            List<String> tagsToSearch = argMultimap.getAllValues(PREFIX_ALIAS);
            predicates.add(new TagContainsKeywordPredicate(tagsToSearch));
        }


        // Combine predicates with AND logic
        Predicate<Patient> combinedPredicate = predicates.stream()
                .reduce(p -> true, Predicate::and);

        return new ListCommand(combinedPredicate);
    }

}
