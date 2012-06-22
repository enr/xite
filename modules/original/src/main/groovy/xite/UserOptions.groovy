package xite

// --- user options management ---
class UserOptions
{
    def source
    def destination
    def environment
    def port
    def action = 'process'
    
    def UserOptions(args)
    {
      def cli = new CliBuilder(usage: 'xite.groovy')
      // Create the list of options.
      cli.with {
          h longOpt: 'help', 'Show usage information'
          s longOpt: 'source', args: 1, argName: 'source', 'The source dir'
          d longOpt: 'destination', args: 1, argName: 'destination', 'The destination dir'
          e longOpt: 'environment', args: 1, argName: 'environment', 'The environment'
          p longOpt: 'port', args: 1, argName: 'port', 'The port [makes sense only for "run" action]'
      }
      
      def options = cli.parse(args)

      if (options.h) {
          cli.usage() 
          return
      }
      
      source = options.'source' ?: null
      destination = options.'destination' ?: null
      environment = options.'environment' ?: null
      port = options.'port' ?: null
      
      def extraArguments = options.arguments()
      if (extraArguments) {
        action = extraArguments[0]
      }

    }

    String toString()
    {
        return "source: ${this.source}, destination: ${this.destination}, environment: ${this.environment}, port: ${this.port}, action: ${this.action}"
    }
}
