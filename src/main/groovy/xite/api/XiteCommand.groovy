package xite.api

interface XiteCommand
{
    CommandResult init();
    CommandResult execute();
    CommandResult cleanup();
}
